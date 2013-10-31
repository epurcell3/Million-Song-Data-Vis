__author__ = 'Ryan'
class GenreBase:
    def __init__(self, songBase):
        self.fulllist = {}
        self.zeroslist = []
        notEmpty = True
        while notEmpty:
            currentSong = songBase.getNext()
            if currentSong is None:
                notEmpty = False
            else:
                for keyword in currentSong.keywords:
                    if self.fulllist[keyword] is None:
                        self.fulllist[keyword] = Genre(keyword, currentSong)
                    else:
                        self.fulllist[keyword].addSong(currentSong)
        for k,v in self.fulllist.items():
            parent = self.findMaxRank(k)
            if parent is not None:
                self.fulllist[parent].children.append(v)
        for gen in self.fulllist.values():
            if gen.rank == 0:
                self.zeroslist.append(gen)

    def findMaxRank(self, keyword):
        current = self.fulllist[keyword]
        maxRank = 0
        maxKey = None
        for key in current.minimalList.values():
            if(self.fulllist[key].rank > maxRank):
                maxRank = self.fulllist[key].rank
                maxKey = key
        return maxKey


class Genre:
    def __init__(self, keyword, initialSong):
        self.keyword = keyword
        self.minimalList = initialSong.keywords
        self.songCount = 0
        self.children = []
        self.rank = len(initialSong.keywords)

    def addSong(self, song):
        self.songCount += 1
        self.minListAdjust(song.keywords)

    def minListAdjust(self, newList):
        for currents in self.minimalList.keys():
            if (newList[currents] is None):
                del self.minimalList[currents]
                self.rank = len(self.minimalList)



class Song:
    def __init__(self, keywords):
        self.keywords = keywords



